import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { BeneficioService } from '../../../../services/beneficio.service';
import { BeneficioDTO } from '../../../../models/beneficio.dto';

@Component({
  standalone: true,
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.scss'],
  imports: [
    CommonModule,
    RouterLink,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    CurrencyPipe
  ]
})
export class ListarComponent implements OnInit {
  displayedColumns = ['id', 'nome', 'descricao', 'valor', 'ativo', 'acoes'];
  dataSource: BeneficioDTO[] = [];
  loading = true;

  constructor(private service: BeneficioService) {}

  ngOnInit(): void {
    this.service.listar().subscribe({
      next: data => { this.dataSource = data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  deletar(id: number) {
    if (confirm('Deseja remover?')) {
      this.service.deletar(id).subscribe(() => {
        this.dataSource = this.dataSource.filter(x => x.id !== id);
      });
    }
  }
}
