import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ProgressBarModule } from 'primeng/progressbar';

interface OrcamentoItem {
  id: string;
  categoria: string;
  orcado: number;
  realizado: number;
}

@Component({
  selector: 'app-orcamento-page',
  standalone: true,
  imports: [CommonModule, ButtonModule, TableModule, ProgressBarModule],
  templateUrl: './orcamento.page.html',
  styleUrls: ['./orcamento.page.scss']
})
export class OrcamentoPage {
  orcamento = signal<OrcamentoItem[]>([
    { id: '1', categoria: 'Alimentação', orcado: 1500, realizado: 1200 },
    { id: '2', categoria: 'Moradia', orcado: 2500, realizado: 2500 },
    { id: '3', categoria: 'Transporte', orcado: 800, realizado: 950 },
    { id: '4', categoria: 'Lazer', orcado: 500, realizado: 200 }
  ]);

  getPercentual(item: OrcamentoItem): number {
    if (item.orcado === 0) return 100;
    const perc = Math.round((item.realizado / item.orcado) * 100);
    return perc > 100 ? 100 : perc;
  }

  isEstourado(item: OrcamentoItem): boolean {
    return item.realizado > item.orcado;
  }
}
