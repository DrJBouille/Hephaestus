import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CreateWorldsRequestDto } from '../../../shared/types/world/CreateWorldsRequestDto';
import { World } from '../../../shared/types/world/World';

@Injectable({
  providedIn: 'root',
})
export class WorldService {
  private readonly apiUrl = `${environment.apiUrl}/worlds`;
  private http = inject(HttpClient);

  getWorlds(page: number, size: number) {
    return this.http.get<PageResult<World>>(this.apiUrl, { params: { page: page, size: size } });
  }

  createWorld(request: CreateWorldsRequestDto) {
    return this.http.post<World>(this.apiUrl, request);
  }

  upload(id: String, file: File) {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<World>(this.apiUrl + `/upload/${id}`, formData);
  }

  download(id: String) {
    return this.http.get(this.apiUrl + `/download/${id}`, { responseType: 'blob', observe: 'response' });
  }
}
