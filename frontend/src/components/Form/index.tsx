import React, { FormEvent, useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import { Map, TileLayer, Marker } from 'react-leaflet';
import { LeafletMouseEvent } from 'leaflet';
import { useHistory } from 'react-router';

import mapIcon from '../../utils/mapIcon';
import api from '../../services/api';

import './styles.css';

interface FormProps {
	willRegister: string;
	reference: string;
}

const Form: React.FC<FormProps> = ({ reference, willRegister }: FormProps) => {
	const [name, setName] = useState('');
	const [code, setCode] = useState('');
	const [start, setStart] = useState<Date | null>(null);
	const [end, setEnd] = useState<Date | null>(null);
	const [point, setPoint] = useState({ latitude: 0, longitude: 0 });
	const [currentPosition, setCurrentPosition] = useState({ latitude: 0, longitude: 0 });
	const [next, setNext] = useState(false);

	const history = useHistory();

	useEffect(() => {
		if (willRegister === 'fields') {
			navigator.geolocation.getCurrentPosition(position =>
				setCurrentPosition({
					latitude: position.coords.latitude,
					longitude: position.coords.longitude,
				}),
			);
		}
	}, []);

	function handleStartDate(date: Date | [Date, Date] | null) {
		if (!date) {
			setStart(date);
		} else {
			setStart(date as Date);
		}
	}

	function handleEndDate(date: Date | [Date, Date] | null) {
		if (!date) {
			setEnd(date);
		} else {
			setEnd(date as Date);
		}
	}

	async function handleSubmit(event: FormEvent) {
		event.preventDefault();
		let id;

		switch (willRegister) {
			case 'harvests':
				id = { millId: reference };
				break;

			case 'farms':
				id = { harvestId: reference };
				break;

			case 'fields':
				id = { farmId: reference };
				break;

			default:
				id = {};
				break;
		}

		await api.post(`/${willRegister}`, {
			name,
			code,
			start,
			end,
			...id,
			...(point.latitude !== 0 && { geom: { type: 'Point', coordinates: [point.latitude, point.longitude] } }),
		});

		history.push('/');
	}

	function handleMapClick(event: LeafletMouseEvent) {
		const { lat, lng } = event.latlng;

		setPoint({ latitude: lat, longitude: lng });
	}

	if (!next && willRegister === 'fields') {
		return (
			<div style={{ display: 'flex', flexDirection: 'column' }}>
				<h3 id="map-field-title">Select the location where the field is located:</h3>
				<Map
					center={[currentPosition.latitude, currentPosition.longitude]}
					style={{ width: '100%', height: 580 }}
					zoom={15}
					onClick={handleMapClick}
				>
					<TileLayer
						url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/256/{z}/{x}/{y}@2x?access_token=${process.env.REACT_APP_MAPBOX_TOKEN}`}
					/>

					{point.latitude !== 0 && <Marker interactive={false} icon={mapIcon} position={[point.latitude, point.longitude]} />}
				</Map>
				<button onClick={() => setNext(true)} disabled={point.latitude === 0} id="map-button-continue">
					Continue
				</button>
			</div>
		);
	}

	return (
		<form id="form-register" onSubmit={handleSubmit}>
			<fieldset>
				<legend>Insert the data</legend>
				{['mills', 'farms'].includes(willRegister) && (
					<div id="input-container">
						<legend>Name</legend>
						<input
							value={name}
							onChange={e => setName(e.target.value)}
							type="text"
							name="name"
							className="search-input register-input"
							placeholder="Enter a name"
						/>
					</div>
				)}

				{['harvests', 'farms', 'fields'].includes(willRegister) && (
					<div id="input-container">
						<legend>Code</legend>
						<input
							value={code}
							onChange={e => setCode(e.target.value)}
							type="text"
							name="code"
							className="search-input register-input"
							placeholder="Enter a code"
						/>
					</div>
				)}

				{willRegister === 'harvests' && (
					<>
						<div id="input-container">
							<legend>Start date</legend>
							<DatePicker selected={start} isClearable onChange={handleStartDate} placeholderText="Start Date" />
						</div>
						<div id="input-container">
							<legend>End date</legend>
							<DatePicker selected={end} isClearable onChange={handleEndDate} placeholderText="End Date" />
						</div>
					</>
				)}

				<button type="submit" id="register-button">
					Register
				</button>
			</fieldset>
		</form>
	);
};

export default Form;
