export interface Mills {
  id: number;
  name: string;
  harvests: Array<Harvests>
}

export interface Harvests {
  id: number;
  code: string;
  start: Date;
  end: Date;
}