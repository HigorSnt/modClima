import React, { ChangeEvent, KeyboardEvent, useEffect, useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';

import FieldItem from '../../../components/FieldItem';
import Loading from '../../../components/Loading';

import { Fields } from '../../../@types';

import api from '../../../services/api';

const FieldsList: React.FC = () => {
	const [fields, setFields] = useState<Fields[]>([]);
	const [code, setCode] = useState('');
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		handleSearch();
	}, []);

	async function handleSearch() {
		try {
			setLoading(true);
			const response = await api.get('fields', { params: { code } });

			setFields(response.data);
		} catch (error) {
			setLoading(false);
		} finally {
			setLoading(false);
		}
	}

	function handleCode(event: ChangeEvent<HTMLInputElement>) {
		setCode(event.target.value);
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
					value={code}
					onChange={handleCode}
					className="search-input"
					onKeyPress={handleKeyPress}
					placeholder="Search for a field by its code"
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
						<p>Code</p>
					</div>
					<div className="scrollable-area">
						{fields.map(field => (
							<FieldItem key={field.id} field={field} />
						))}
					</div>
				</div>
			)}
		</div>
	);
};

export default FieldsList;
