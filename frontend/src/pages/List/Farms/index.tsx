import React, { ChangeEvent, KeyboardEvent, useEffect, useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import Dropdown, { Option } from 'react-dropdown';

import FarmItem from '../../../components/FarmItem';
import Loading from '../../../components/Loading';

import { Farms } from '../../../@types';

import api from '../../../services/api';

import 'react-dropdown/style.css';
import './styles.css';

const options = [
	{ value: 'name', label: 'Name' },
	{ value: 'code', label: 'Code' },
];

const FarmsList: React.FC = () => {
	const [farms, setFarms] = useState<Farms[]>([]);
	const [name, setName] = useState('');
	const [code, setCode] = useState('');
	const [selected, setSelected] = useState('name');
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		handleSearch();
	}, []);

	async function handleSearch() {
		try {
			setLoading(true);
			const response = await api.get('farms', { params: { name, code } });

			setFarms(response.data);
		} catch (error) {
			setLoading(false);
		} finally {
			setLoading(false);
		}
	}

	function handleName(event: ChangeEvent<HTMLInputElement>) {
		setName(event.target.value);
	}

	function handleKeyPress(event: KeyboardEvent<HTMLInputElement>) {
		if (event.key === 'Enter') {
			handleSearch();
		}
	}

	function handleCode(event: ChangeEvent<HTMLInputElement>) {
		setCode(event.target.value);
	}

	function handleToggleInputParam(arg: Option) {
		setSelected(arg.value);
	}

	return (
		<div className="list-container-area">
			<div id="farm-search-box">
				<Dropdown
					className="dropdown-container"
					controlClassName="dropdown"
					placeholderClassName="dropdown-placeholder"
					options={options}
					placeholder="Search by..."
					onChange={handleToggleInputParam}
				/>
				<div className="search-container">
					<input
						value={selected === 'name' ? name : code}
						onChange={selected === 'name' ? handleName : handleCode}
						className="search-input"
						onKeyPress={handleKeyPress}
						placeholder={`Search for a field by its ${selected}`}
					/>
					<button
						className="search-button"
						onClick={e => {
							e.preventDefault();
							handleSearch();
						}}
					>
						<AiOutlineSearch size={25} color="#fff" />
					</button>
				</div>
			</div>

			{loading && (
				<div id="loading-container">
					<Loading />
				</div>
			)}

			{!loading && (
				<div id="items-container">
					<div id="items-header" className="items">
						<p>ID</p>
						<p>Code</p>
						<p>Name</p>
					</div>
					<div className="scrollable-area">
						{farms.map(farm => (
							<FarmItem key={farm.id} farm={farm} />
						))}
					</div>
				</div>
			)}
		</div>
	);
};

export default FarmsList;
