import React, { ChangeEvent, KeyboardEvent, useEffect, useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

import Loading from '../../../components/Loading';
import MillItem from '../../../components/MillItem';

import { Mills } from '../../../@types';

import api from '../../../services/api';

import './styles.css';

const MillsList: React.FC = () => {
	const [mills, setMills] = useState<Mills[]>([]);
	const [name, setName] = useState('');
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		handleSearch();
	}, []);

	async function handleSearch() {
		try {
			setLoading(true);
			const response = await api.get('mills', { params: { name } });

			setMills(response.data);
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

	return (
		<div className="list-container-area">
			<div className="search-container">
				<input
					value={name}
					onChange={handleName}
					className="search-input"
					onKeyPress={handleKeyPress}
					placeholder="Search for a mill by its name"
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

			{loading && (
				<div id="loading-container">
					<Loading />
				</div>
			)}

			{!loading && (
				<div id="items-container">
					<div id="items-header" className="items">
						<p>ID</p>
						<p>Name</p>
					</div>
					<div className="scrollable-area">
						{mills.map(mill => (
							<MillItem key={mill.id} mill={mill} />
						))}
					</div>
				</div>
			)}
		</div>
	);
};

export default MillsList;
