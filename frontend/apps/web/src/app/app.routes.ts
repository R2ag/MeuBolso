import { Routes } from '@angular/router';
import { ImportPage } from './pages/import/import.page';

export const routes: Routes = [
  { path: '', redirectTo: 'import', pathMatch: 'full' },
  { path: 'import', component: ImportPage }
];
