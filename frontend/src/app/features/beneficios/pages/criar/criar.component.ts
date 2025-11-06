import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BeneficioService } from '../../../../services/beneficio.service';
import { BeneficioDTO } from '../../../../models/beneficio.dto';

@Component({
  standalone: true,
  templateUrl: './criar.component.html',
  styleUrls: ['./criar.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatCardModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ]
})
export class CriarComponent implements OnInit {
  form!: FormGroup;
  saving = false;
  editMode = false;
  id?: number;

  constructor(
    private fb: FormBuilder,
    private service: BeneficioService,
    private router: Router,
    private route: ActivatedRoute,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    this.buildForm();

    // verifica se tem :id na rota
    const paramId = this.route.snapshot.paramMap.get('id');
    if (paramId) {
      this.editMode = true;
      this.id = +paramId;
      this.carregarBeneficio(this.id);
    }
  }

  private buildForm() {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      descricao: ['', [Validators.required, Validators.minLength(5)]],
      valor: [null, [Validators.required, Validators.min(0.01)]],
      ativo: [true, [Validators.required]]
    });
  }

  private carregarBeneficio(id: number) {
    this.service.buscarPorId(id).subscribe({
      next: (b) => this.form.patchValue(b),
      error: (err) => {
        console.error(err);
        this.snack.open('Erro ao carregar benefício.', 'Fechar', { duration: 4000 });
        this.router.navigate(['/']);
      }
    });
  }

  get f() { return this.form.controls; }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    const payload = this.form.getRawValue() as BeneficioDTO;

    const request$ = this.editMode && this.id
      ? this.service.atualizar(this.id, payload)
      : this.service.criar(payload);

    request$.subscribe({
      next: () => {
        this.snack.open(
          this.editMode ? 'Benefício atualizado com sucesso!' : 'Benefício criado com sucesso!',
          'OK',
          { duration: 3000 }
        );
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        this.snack.open('Erro ao salvar benefício.', 'Fechar', { duration: 4000 });
        this.saving = false;
      }
    });
  }
}
