import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

interface StagingItem {
  date: string;
  description: string;
  amount: string;
  category: string;
}

@Component({
  selector: 'import-page',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule],
  templateUrl: './import.page.html',
  styleUrls: ['./import.page.scss']
})
export class ImportPage {
  protected readonly uploadText = signal('');
  protected readonly imported = signal(false);
  protected readonly staging = signal<StagingItem[]>([
    {
      date: '2026-04-10',
      description: 'Assinatura de software',
      amount: '-249.90',
      category: 'Tecnologia'
    },
    {
      date: '2026-04-09',
      description: 'Pagamento de energia',
      amount: '-137.42',
      category: 'Contas'
    },
    {
      date: '2026-04-08',
      description: 'Recebimento PIX',
      amount: '+3.200,00',
      category: 'Receita'
    }
  ]);

  protected preview(): void {
    const raw = this.uploadText();
    if (!raw.trim()) {
      return;
    }

    const lines = raw
      .split(/\r?\n/)
      .map((line) => line.trim())
      .filter(Boolean);

    const parsed = lines
      .map((line) => line.split(';').map((value) => value.trim()))
      .filter((parts) => parts.length >= 4)
      .map(([date, description, amount, category]) => ({
        date,
        description,
        amount,
        category
      }));

    if (parsed.length) {
      this.staging.set(parsed);
      this.imported.set(false);
    }
  }

  protected confirmImport(): void {
    if (!this.staging().length) {
      return;
    }

    this.imported.set(true);
  }

  protected get hasStaging(): boolean {
    return this.staging().length > 0;
  }
}
