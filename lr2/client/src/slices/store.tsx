import { configureStore } from '@reduxjs/toolkit';
import productReducer from './productSlice';
import waybillReducer from './waybillsSlice';
import customerReducer from './customerSlice'
import destinationReducer from "./destinationSlice"
const store = configureStore({
  reducer: {
    products: productReducer,
    waybills: waybillReducer,
    customers: customerReducer,
    destinations: destinationReducer
  },
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store