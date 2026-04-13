import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

interface Lancamento {
  id: string;
  data: string;
  descricao: string;
  valor: number;
  categoria: string;
  tipo: 'RECEITA' | 'DESPESA';
}

@Component({
  selector: 'app-lancamentos-page',
  standalone: true,
  imports: [CommonModule, ButtonModule, TableModule],
  templateUrl: './lancamentos.page.html',
  styleUrls: ['./lancamentos.page.scss']
})
export class LancamentosPage {
  lancamentos = signal<Lancamento[]>([
    { id: '1', data: '2026-04-10', descricao: 'Salário', valor: 8500, categoria: 'Renda', tipo: 'RECEITA' },
    { id: '2', data: '2026-04-12', descricao: 'Supermercado', valor: -450.50, categoria: 'Alimentação', tipo: 'DESPESA' },
    { id: '3', data: '2026-04-13', descricao: 'Internet', valor: -120, categoria: 'Contas', tipo: 'DESPESA' }
  ]);

  getSaldo() {
    return this.lancamentos().reduce((acc, curr) => acc + curr.valor, 0);
  }
}
