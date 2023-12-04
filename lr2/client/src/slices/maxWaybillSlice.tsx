import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'


export interface maxWaybillRow {
    id: number,
    customer_name: string,
    date: string,
    price: string
}

interface maxWaybillState {
    list: maxWaybillRow[];
}

const initialState = {
    list: []
} as maxWaybillState

const maxWaybillsSlice = createSlice({
    name: 'maxWaybill',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(getAllMaxWaybills.fulfilled, (state, action) => {
            console.log(action.payload)
            state.list = action.payload
        })


    }
})

const getAllMaxWaybills = createAsyncThunk(
    'maxWaybills/getAll',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/utils/max_waybills_per_date", {
            method: "GET"
        }).then(response => response.json()) as maxWaybillRow[]
        return response;
    }
)



export { getAllMaxWaybills }

export default maxWaybillsSlice.reducer