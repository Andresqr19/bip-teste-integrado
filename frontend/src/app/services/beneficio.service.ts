import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BeneficioDTO } from '../models/beneficio.dto';
import { TransferRequest } from '../models/transfer-request';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  private api = 'http://localhost:8081/beneficios';

  constructor(private http: HttpClient) {}

  listar(): Observable<BeneficioDTO[]> {
    return this.http.get<BeneficioDTO[]>(this.api);
  }

  buscarPorId(id: number): Observable<BeneficioDTO[]> {
    return this.http.get<BeneficioDTO[]>(`${this.api}/${id}`);
  }

  criar(data: BeneficioDTO): Observable<BeneficioDTO> {
    return this.http.post<BeneficioDTO>(this.api, data);
  }

  atualizar(id: number, data: BeneficioDTO): Observable<BeneficioDTO> {
    return this.http.put<BeneficioDTO>(`${this.api}/${id}`, data);
  }

  deletar(id: number) {
    return this.http.delete(`${this.api}/${id}`);
  }

  transfer(req: TransferRequest) {
    return this.http.post<{ message: string }>(`${this.api}/transfer`, req);
  }
}
