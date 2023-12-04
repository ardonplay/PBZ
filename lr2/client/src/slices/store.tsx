import { configureStore } from '@reduxjs/toolkit';
import productReducer from './productSlice';
import waybillReducer from './waybillsSlice';
import customerReducer from './customerSlice'
import destinationReducer from "./destinationSlice"
import maxWaybillReducer from './maxWaybillSlice'
import priceByIdReducer from "./findPriceSlice"
const store = configureStore({
  reducer: {
    products: productReducer,
    waybills: waybillReducer,
    customers: customerReducer,
    destinations: destinationReducer,
    maxWaybill: maxWaybillReducer,
    priceById: priceByIdReducer
  },
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store