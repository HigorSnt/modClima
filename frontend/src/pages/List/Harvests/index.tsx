import React, { useState, useEffect } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import DatePicker from 'react-datepicker';
import { format, compareAsc } from 'date-fns';

import HarvestItem from '../../../components/HarvestItem';
import Loading from '../../../components/Loading';

import { Harvests } from '../../../@types';

import api from '../../../services/api';

import 'react-datepicker/dist/react-datepicker.css';
import './styles.css';

const HarvestsList: React.FC = () => {
	const [harvests, setHarvests] = useState<Harvests[]>([]);
	const [loading, setLoading] = useState(false);
	const [startDate, setStartDate] = useState<Date | null>(null);
	const [endDate, setEndDate] = useState<Date | null>(null);
	const [error, setError] = useState('');

	useEffect(() => {
		handleSearch();
	}, []);

	async function handleSearch() {
		try {
			setLoading(true);

			if (compareAsc(startDate as Date, endDate as Date) >= 0) {
				setError('Start date must be before the end date!');
				return;
			}

			const response = await api.get('harvests', {
				params: {
					end: formatDate(endDate as Date),
					start: formatDate(startDate as Date),
				},
			});

			setHarvests(response.data);
		} catch (error) {
			console.log(error);
			setLoading(false);
		} finally {
			setLoading(false);
		}
	}

	const formatDate = (date: Date): string => {
		if (!date) {
			return '';
		}

		return format(date, 'yyyy-MM-dd');
	};

	function handleStartDate(date: Date | [Date, Date] | null) {
		if (!date) {
			setError('');
			setStartDate(date);
		} else {
			setStartDate(date as Date);
		}
	}

	function handleEndDate(date: Date | [Date, Date] | null) {
		if (!date) {
			setError('');
			setEndDate(date);
		} else {
			setEndDate(date as Date);
		}
	}

	return (
		<div className="list-container-area">
			<div id="container">
				<div id="search-date-container">
					<div>
						<DatePicker selected={startDate} isClearable onChange={handleStartDate} placeholderText="Start Date" />
					</div>
					<div>
						<DatePicker selected={endDate} isClearable onChange={handleEndDate} placeholderText="End Date" />
					</div>
					<button
						id="search-button-date"
						className="search-button"
						onClick={e => {
							e.preventDefault();
							handleSearch();
						}}
					>
						<AiOutlineSearch size={25} color="#fff" />
					</button>
				</div>
				<div id="error">
					<p>{error}</p>
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
						<p>Start</p>
						<p>End</p>
					</div>
					<div className="scrollable-area">
						{harvests.map(harvest => (
							<HarvestItem key={harvest.id} harvest={harvest} />
						))}
					</div>
				</div>
			)}
		</div>
	);
};

export default HarvestsList;
