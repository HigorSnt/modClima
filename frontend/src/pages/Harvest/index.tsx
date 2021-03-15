import React, { useEffect, useState } from 'react';
import { RiPlantFill } from 'react-icons/ri';
import { FaBarcode } from 'react-icons/fa';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import Skeleton from 'react-loading-skeleton';

import Card from '../../components/Card';
import Menu from '../../components/Menu';
import Empty from '../../components/Empty';

import { Harvests, Param } from '../../@types';

import api from '../../services/api';

import './styles.css';

const HarvestDetails: React.FC = () => {
	const params = useParams<Param>();

	const [harvest, setHarvest] = useState<Harvests>();

	useEffect(() => {
		api.get(`harvests/${params.id}`).then(({ data }) => {
			setHarvest(data);
		});
	}, [params.id]);

	if (!harvest) {
		return (
			<div id="harvest-details-box">
				<Menu />
				<div id="harvest-info-container">
					<div id="harvest-title">
						<Skeleton width={300} height={60} />
					</div>
					<div id="harvests-farms">
						<Skeleton width={300} height={160} count={4} style={{ margin: 20 }} />
					</div>
				</div>
			</div>
		);
	}

	return (
		<div id="harvest-details-box">
			<Menu />
			<div id="harvest-info-container">
				<div id="harvest-title">
					<RiPlantFill size={60} color="#05aff2" />
					<span>{`Harvest ${harvest.code}`}</span>
				</div>

				<div id="harvest-farms">
					{harvest.farms?.length === 0 && <Empty />}
					{harvest.farms?.map(farm => (
						<Card key={harvest.id}>
							<Link to={`/farms/${farm.id}`} className="item-link">
								<div className="harvests-farm-items">
									<div className="harvest-title-box">
										<span className="harvest-title">Farm Name:</span>
										<p className="harvest-title">
											<strong>{farm.name}</strong>
										</p>
									</div>
									<div className="harvest-farm-info-area">
										<FaBarcode size={25} color="#383D3B" />
										<span>{farm.code}</span>
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

export default HarvestDetails;
