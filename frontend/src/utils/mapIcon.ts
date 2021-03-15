import Leaflet from 'leaflet';

import markerImg from '../images/marker.svg';

const mapIcon = Leaflet.icon({
	iconUrl: markerImg,
	iconSize: [38, 48],
	iconAnchor: [20, 55],
});

export default mapIcon;
