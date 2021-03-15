export interface Mills {
	id: number;
	name: string;
	harvests?: Array<Harvests>;
}

export interface Harvests {
	id: number;
	code: string;
	start: Date;
	end: Date;
	mill: Mills;
	farms?: Array<Farms>;
}

export interface Farms {
	id: number;
	code: string;
	name: string;
	harvest: Harvests;
	fields?: Array<Fields>;
}

export interface Fields {
	id: number;
	code: string;
	geom: Point;
	farm: Farms;
}

export interface Point {
	type: string;
	coordinates: Array<number>;
}

export interface Param {
	id: string;
}

export interface Item {
	id: string;
	code?: string;
	name?: string;
}

export interface Notification {
	message: string;
	redirectURL: string;
}
