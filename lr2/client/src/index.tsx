import { render } from 'preact';
import { LocationProvider, Router, Route } from 'preact-iso';

import { Home } from './pages/Home/index.jsx';
import { NotFound } from './pages/_404.jsx';
import './style.css';
import Dashboard from './components/Dashboard/index.js';
import Products from './pages/Products/index.js';
import Customers from './pages/Customers/index.js';
import Waybills from './pages/Waybills/index.js';
import Utils from './pages/Utils/index.js'
import Destinations from './pages/Destinations/index.js';
import { Provider } from 'react-redux';
import store from './slices/store';
import MaxWaybillsPerDate from './pages/Utils/MaxWaybillsPerDate.js';
import FindPriceByProductId from './pages/Utils/FindPriceByProductId.js';
import FindWaybillsByProductId from './pages/Utils/FindWaybillsByProduct.js';

const App = () => {
	return (
		<LocationProvider>
			<div class="home flex flex-row justify-start ">
				<Dashboard />
				<div class="p-4 flex flex-col w-full h-screen dark:bg-slate-900 bg-slate-600">
					<Router>
						<Route path="/" component={Home} />
						<Route path='/products' component={Products} />
						<Route path='/customers' component={Customers} />
						<Route path='/waybills' component={Waybills} />
						<Route path='/destinations' component={Destinations} />
						<Route path='/utils' component={Utils} />
						<Route path="/utils/max_waybills_per_date" component={MaxWaybillsPerDate} />
						<Route path="/utils/find_price_by_product_id" component={FindPriceByProductId} />
						<Route path="/utils/find_waybills_by_product" component={FindWaybillsByProductId} />
						<Route default component={NotFound} />
					</Router>
				</div>
			</div>
		</LocationProvider>
	)
}


render(

	<Provider store={store}>
		<App />
	</Provider>


	, document.getElementById('app'));