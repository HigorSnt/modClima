import React from 'react';
import { Link } from 'react-router-dom';

import { Mills } from '../../@types';

interface MillItemProps {
	mill: Mills;
}

const MillItem: React.FC<MillItemProps> = ({ mill }: MillItemProps) => {
	return (
		<Link to={`/mills/${mill.id}`} title="Show Details" className="items button-items">
			<p>{mill.id}</p>
			<p>{mill.name}</p>
		</Link>
	);
};

export default MillItem;
