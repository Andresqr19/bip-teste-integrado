import { Routes } from '@angular/router';
import { ListarComponent } from './features/beneficios/pages/listar/listar.component';
import { CriarComponent } from './features/beneficios/pages/criar/criar.component';
import { TransferComponent } from './features/beneficios/pages/transfer/transfer.component';

export const routes: Routes = [
  { path: '', component: ListarComponent, title: 'Benefícios' },
  { path: 'novo', component: CriarComponent, title: 'Novo Benefício' },
  { path: 'transfer', component: TransferComponent, title: 'Transferência' },
  { path: '**', redirectTo: '' }
];
