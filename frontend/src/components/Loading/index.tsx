import React from 'react';
import Loader from 'react-loader-spinner';

import 'react-loader-spinner/dist/loader/css/react-spinner-loader.css';

const Loading: React.FC = () => {
	return <Loader type="Oval" color="#05aff2" height={80} width={80} />;
};

export default Loading;
