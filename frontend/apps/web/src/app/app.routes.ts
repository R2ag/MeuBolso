import { Routes } from '@angular/router';
import { ImportPage } from './pages/import/import.page';
import { LoginPage } from './pages/login/login.page';
import { LancamentosPage } from './pages/lancamentos/lancamentos.page';
import { OrcamentoPage } from './pages/orcamento/orcamento.page';
import { DividasPage } from './pages/dividas/dividas.page';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginPage },
  { path: 'import', component: ImportPage },
  { path: 'lancamentos', component: LancamentosPage },
  { path: 'orcamento', component: OrcamentoPage },
  { path: 'dividas', component: DividasPage }
];
