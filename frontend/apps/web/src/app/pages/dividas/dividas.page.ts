import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

interface Divida {
  id: string;
  descricao: string;
  valorTotal: number;
  parcelasPagas: number;
  parcelasTotal: number;
  status: 'ATIVA' | 'QUITADA';
}

@Component({
  selector: 'app-dividas-page',
  standalone: true,
  imports: [CommonModule, ButtonModule, TableModule],
  templateUrl: './dividas.page.html',
  styleUrls: ['./dividas.page.scss']
})
export class DividasPage {
  dividas = signal<Divida[]>([
    { id: '1', descricao: 'Financiamento Carro', valorTotal: 45000, parcelasPagas: 12, parcelasTotal: 48, status: 'ATIVA' },
    { id: '2', descricao: 'Empréstimo Pessoal', valorTotal: 5000, parcelasPagas: 24, parcelasTotal: 24, status: 'QUITADA' },
    { id: '3', descricao: 'Notebook', valorTotal: 3400, parcelasPagas: 3, parcelasTotal: 10, status: 'ATIVA' }
  ]);
}
