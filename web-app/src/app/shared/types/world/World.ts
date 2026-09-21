import { Environment } from './Environment';
import { WorldType } from './WorldType';
import { WorldStatus } from './WorldStatus';

export interface World {
  id: string,
  name: string,
  environment: Environment,
  worldType: WorldType,
  status: WorldStatus,
  createdAt: string,
  updatedAt: string
}
