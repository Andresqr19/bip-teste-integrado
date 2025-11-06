import { Routes } from '@angular/router';
import { CriarComponent } from './features/beneficios/pages/criar/criar.component';
import { TransferComponent } from './features/beneficios/pages/transfer/transfer.component';
import { ListarComponent } from './features/beneficios/pages/listar/listar.component';

export const routes: Routes = [
  { path: '', component: ListarComponent },
  { path: 'novo', component: CriarComponent, title: 'Novo Benefício' },
  { path: 'editar/:id', component: CriarComponent, title: 'Editar Benefício' },
  { path: 'transfer', component: TransferComponent },
];
