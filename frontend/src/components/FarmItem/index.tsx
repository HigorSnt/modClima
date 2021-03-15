import React from 'react';
import { Link } from 'react-router-dom';

import { Farms } from '../../@types';

interface FarmItemProps {
	farm: Farms;
}

const FarmItem: React.FC<FarmItemProps> = ({ farm }: FarmItemProps) => {
	return (
		<Link to={`farms/${farm.id}`} title="Show Details" className="items button-items">
			<p>{farm.id}</p>
			<p>{farm.code}</p>
			<p>{farm.name}</p>
		</Link>
	);
};

export default FarmItem;
