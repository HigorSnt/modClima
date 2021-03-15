import React from 'react';
import { Link } from 'react-router-dom';

import { Harvests } from '../../@types';

interface HarvestsItemProps {
	harvest: Harvests;
}

const HarvestItem: React.FC<HarvestsItemProps> = ({ harvest }: HarvestsItemProps) => {
	return (
		<Link to={`/harvests/${harvest.id}`} title="Show Details" className="items button-items">
			<p>{harvest.id}</p>
			<p>{harvest.code}</p>
			<p>{harvest.start}</p>
			<p>{harvest.end}</p>
		</Link>
	);
};

export default HarvestItem;
