import React, { ChangeEvent, KeyboardEvent, useEffect, useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

import { Mills } from '../../../@types';

import api from '../../../services/api';

import './styles.css';

const MillsList: React.FC = () => {
	const [mills, setMills] = useState<Mills[]>([]);
	const [name, setName] = useState('');

	useEffect(() => {
		handleSearch();
	}, []);

	async function handleSearch() {
		const response = await api.get('mills', { params: { name } });
		console.log(response.data);

		setMills(response.data);
	}

	function handleName(event: ChangeEvent<HTMLInputElement>) {
		setName(event.target.value);
	}

	function handleKeyPress(event: KeyboardEvent<HTMLInputElement>): any {
		if (event.key === 'Enter') {
			console.log('prinionan');
		}
	}

	return (
		<div className="list-container-area">
			<div className="search-container">
				<input
					value={name}
					onChange={handleName}
					className="search-input"
					onKeyPress={handleKeyPress}
					placeholder="Search for the name of a mill"
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

			<div id="items-container">
				<div id="items-header items">
					<p>ID</p>
					<p>Name</p>
				</div>
			</div>
		</div>
	);
};

export default MillsList;
