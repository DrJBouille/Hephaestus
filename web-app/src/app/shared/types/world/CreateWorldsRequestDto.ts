import { Environment } from './Environment';
import { WorldType } from './WorldType';

export interface CreateWorldsRequestDto {
  name: string,
  environment: Environment,
  worldType: WorldType
}
