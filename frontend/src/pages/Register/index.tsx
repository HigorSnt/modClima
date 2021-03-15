import React, { useState } from 'react';
import Dropdown, { Option } from 'react-dropdown';

import Menu from '../../components/Menu';
import Form from '../../components/Form';

import { Item } from '../../@types';

import api from '../../services/api';

import './styles.css';

const options = [
	{ value: 'mills', label: 'Mill' },
	{ value: 'harvests', label: 'Harvest' },
	{ value: 'farms', label: 'Farm' },
	{ value: 'fields', label: 'Field' },
];

const Register: React.FC = () => {
	const [selectValue, setSelectValue] = useState('');
	const [selectedItem, setSelectedItem] = useState('');
	const [items, setItems] = useState<Item[]>([]);
	const [next, setNext] = useState(false);

	function handleToggleSelectRegister(arg: Option) {
		setItems([]);
		setSelectValue(arg.value);

		if (arg.value === 'harvests') {
			api.get(`/mills`).then(({ data }) => setItems(data));
		} else if (arg.value === 'farms') {
			api.get(`/harvests`).then(({ data }) => setItems(data));
		} else if (arg.value === 'fields') {
			api.get(`/farms`).then(({ data }) => setItems(data));
		}
	}

	function handleToggleSelectedItem(item: Option) {
		setSelectedItem(item.value);
	}

	function generateLegendText() {
		switch (selectValue) {
			case 'harvests':
				return 'Which mill do you want to add a new harvest to?';

			case 'farms':
				return 'Which harvest do you want to add a new farm to?';

			case 'fields':
				return 'Which farm do you want to add a new field to?';

			default:
				return '';
		}
	}

	return (
		<div className="container">
			<Menu />
			<div id="register-container">
				{!next && (
					<>
						<fieldset>
							<legend>What do you want to register?</legend>
							<Dropdown
								className="register-dropdown-container"
								controlClassName="dropdown"
								placeholderClassName="dropdown-placeholder"
								options={options}
								placeholder="Select"
								onChange={handleToggleSelectRegister}
							/>
						</fieldset>
						{items.length > 0 && (
							<>
								<div className="separator" />
								<fieldset>
									<legend>{generateLegendText()}</legend>
									<Dropdown
										className="register-dropdown-container"
										controlClassName="dropdown"
										placeholderClassName="dropdown-placeholder"
										options={items.map(item => {
											return { label: `${item.id} - ${item.name ?? item.code ?? ''}`, value: item.id };
										})}
										placeholder="Select"
										onChange={handleToggleSelectedItem}
									/>
								</fieldset>
							</>
						)}

						<button
							disabled={selectValue === '' || (selectValue !== 'mills' && selectedItem === '')}
							id="register-next"
							onClick={() => setNext(true)}
						>
							Next
						</button>
					</>
				)}
				{next && <Form willRegister={selectValue} reference={selectedItem} />}
			</div>
		</div>
	);
};

export default Register;
