import React, { useEffect, useState } from 'react';
import Skeleton from 'react-loading-skeleton';
import { GiFarmer } from 'react-icons/gi';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';

import Card from '../../components/Card';
import Empty from '../../components/Empty';
import Menu from '../../components/Menu';

import { Farms, Param } from '../../@types';

import api from '../../services/api';

import './styles.css';

const FarmDetails: React.FC = () => {
	const params = useParams<Param>();

	const [farm, setFarm] = useState<Farms>();

	useEffect(() => {
		api.get(`farms/${params.id}`).then(({ data }) => {
			setFarm(data);
		});
	}, [params.id]);

	if (!farm) {
		return (
			<div id="farm-details-box">
				<Menu />
				<div id="farm-info-container">
					<div id="farm-title">
						<Skeleton width={300} height={60} />
					</div>
					<div id="farms-fields">
						<Skeleton width={300} height={160} count={4} style={{ margin: 20 }} />
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
					<GiFarmer size={60} color="#05aff2" />
					<span>{`Farm ${farm.name}`}</span>
				</div>

				<div id="farms-fields">
					{farm.fields?.length === 0 && <Empty />}
					{farm.fields?.map(field => (
						<Card key={field.id}>
							<Link to={`/fields/${field.id}`} className="item-link">
								<div className="farm-fields-items">
									<div className="farm-title-box">
										<span className="farm-title">Field code:</span>
										<p className="farm-title">
											<strong>{field.code}</strong>
										</p>
									</div>
								</div>
							</Link>
						</Card>
					))}
				</div>
			</div>
		</div>
	);
};

export default FarmDetails;
