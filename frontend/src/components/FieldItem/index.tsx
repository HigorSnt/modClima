import React from 'react';
import { Link } from 'react-router-dom';

import { Fields } from '../../@types';

interface FieldItemProps {
	field: Fields;
}

const FieldItem: React.FC<FieldItemProps> = ({ field }: FieldItemProps) => {
	return (
		<Link to={`/fields/${field.id}`} title="Show Details" className="items button-items">
			<p>{field.id}</p>
			<p>{field.code}</p>
		</Link>
	);
};

export default FieldItem;
