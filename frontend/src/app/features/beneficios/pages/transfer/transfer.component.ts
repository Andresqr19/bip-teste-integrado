import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BeneficioService } from '../../../../services/beneficio.service';
import { BeneficioDTO } from '../../../../models/beneficio.dto';
import { TransferRequest } from '../../../../models/transfer-request';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule
  ]
})
export class TransferComponent implements OnInit {
  form!: FormGroup;
  beneficios: BeneficioDTO[] = [];

  lockingModes = [
    { value: 'OPTIMISTIC', label: 'Otimista' },
    { value: 'PESSIMISTIC_WRITE', label: 'Pessimista' },
  ];

  constructor(
    private fb: FormBuilder,
    private service: BeneficioService,
    private snack: MatSnackBar,
    private router: Router
  ) {
    this.form = this.fb.group({
      fromId: [null, Validators.required],
      toId: [null, Validators.required],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      lockingMode: ['OPTIMISTIC', Validators.required]
    });
  }

  ngOnInit(): void {
    this.service.listar().subscribe({
      next: list => this.beneficios = list
    });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const req = this.form.getRawValue() as TransferRequest;
    if (req.fromId === req.toId) {
      this.snack.open('Selecione contas diferentes para origem e destino.', 'Fechar', { duration: 3000 });
      return;
    }

    this.service.transfer(req).subscribe({
      next: () => {
        this.snack.open('Transferência realizada!', 'OK', { duration: 3000 });
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        this.snack.open('Erro na transferência.', 'Fechar', { duration: 4000 });
      }
    });
  }
}
