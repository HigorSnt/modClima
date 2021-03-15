import React, { useEffect, useState } from 'react';
import Skeleton from 'react-loading-skeleton';
import { GiField } from 'react-icons/gi';
import { useParams } from 'react-router';
import { Map, TileLayer, Marker } from 'react-leaflet';

import Menu from '../../components/Menu';

import { Fields, Param } from '../../@types';

import mapIcon from '../../utils/mapIcon';
import api from '../../services/api';

import './styles.css';

const FieldDetails: React.FC = () => {
	const params = useParams<Param>();

	const [field, setField] = useState<Fields>();

	useEffect(() => {
		api.get(`fields/${params.id}`).then(({ data }) => {
			setField(data);
		});
	}, [params.id]);

	if (!field) {
		return (
			<div id="farm-details-box">
				<Menu />
				<div id="farm-info-container">
					<div id="farm-title">
						<Skeleton width={300} height={60} />
					</div>
					<div className="map-container">
						<Skeleton width={'100%'} height={400} style={{ margin: 20 }} />
					</div>
				</div>
			</div>
		);
	}

	return (
		<div id="farm-details-box">
			<Menu />
			<div id="farm-info-container">
				<div id="farm-title">
					<GiField size={60} color="#05aff2" />
					<span>{`Field ${field.code}`}</span>
				</div>

				<div className="map-container">
					<Map
						center={[field.geom.coordinates[0], field.geom.coordinates[1]]}
						zoom={15}
						style={{ width: '100%', height: 400 }}
						dragging
						touchZoom={false}
						zoomControl={false}
						scrollWheelZoom={false}
						doubleClickZoom={false}
					>
						<TileLayer
							url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/256/{z}/{x}/{y}@2x?access_token=${process.env.REACT_APP_MAPBOX_TOKEN}`}
						/>
						<Marker icon={mapIcon} interactive={false} position={[field.geom.coordinates[0], field.geom.coordinates[1]]} />
					</Map>
				</div>
			</div>
		</div>
	);
};

export default FieldDetails;
