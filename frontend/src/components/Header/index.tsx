import React, { useState } from 'react';
import { BiFilter } from 'react-icons/bi';

import './styles.css';

const options = [
	{ key: 'mills', text: 'Mills', value: 'mills' },
	{ key: 'harvests', text: 'Harvests', value: 'harvests' },
	{ key: 'farms', text: 'Farms', value: 'farms' },
	{ key: 'fields', text: 'Fields', value: 'fields' },
];

const Header: React.FC = () => {
	const [value, setValue] = useState('');

	function handleChange() {
		console.log('aaiia');
	}

	return (
		<div id="header-box">
			<div className="filter-box">
				<label htmlFor="show-items">Select what do you want to see...</label>
				<select name="show-items">
					{options.map(option => (
						<option value={option.value} key={option.key}>
							{option.text}
						</option>
					))}
				</select>
			</div>
			<div className="filter-box">
				<button>
					<BiFilter size={40} />
				</button>
				<div />
				<input type="text" name="" id="" />
			</div>
		</div>
	);
};

export default Header;
