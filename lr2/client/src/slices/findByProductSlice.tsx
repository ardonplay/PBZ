import { PayloadAction, createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { waybillRow } from './waybillsSlice'


interface  waybillByProductState {
    list: waybillRow[]
    product_id: number
}

const initialState = {
    list: [], 
    
    product_id: 0
} as waybillByProductState

const priceByProductSlice = createSlice({
    name: 'priceByProduct',
    initialState,
    reducers: {
        setProductId(state, action: PayloadAction<string>) {
           state.product_id = Number.parseInt(action.payload)
        }
    },
    extraReducers: (builder) => {
        builder.addCase(findWaybillsByProductId.fulfilled, (state, action) => {
            state.list = action.payload
        })


    }
})

const findWaybillsByProductId  = createAsyncThunk(
    'waybills/findByProductId',
    async (data: number) => {

        const response = await fetch(`http://localhost:8080/api/v1/waybills?product_id=${data}`, {
            method: "GET", headers: { "Content-Type": "application/json" }
        }).then(response => response.json()) as waybillRow[];
        return response;
    }
)


export { findWaybillsByProductId }
export const {setProductId} = priceByProductSlice.actions;
export default priceByProductSlice.reducer