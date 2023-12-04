import { PayloadAction, createAsyncThunk, createSlice } from '@reduxjs/toolkit'


export interface priceById {
    name: string,
    date: string,
    price: string
}

interface priceByIdState {
    list: priceById[],
    date_end: string, 
    date_start: string,
    id: number
}

const initialState = {
    list: [], 
    date_end: "",
    date_start: "", 
    id: 0
} as priceByIdState

const priceByIdSlice = createSlice({
    name: 'priceById',
    initialState,
    reducers: {
        setProductId(state, action: PayloadAction<string>) {
           state.id = Number.parseInt(action.payload)
        },
        setDateEnd(state, action: PayloadAction<string>){
            state.date_end = action.payload
        },
        setDateStart(state, action: PayloadAction<string>){
            state.date_start = action.payload
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getAllPriceById.fulfilled, (state, action) => {
            console.log(action.payload)
            state.list = action.payload
        })


    }
})

const getAllPriceById = createAsyncThunk(
    'priceById/getAll',
    async (data: { id: number, date_start: string, date_end: string }) => {
        const response = await fetch(`http://localhost:8080/api/v1/utils/find_price_by_product_id?id=${data.id}&date_start=${data.date_start}&date_end=${data.date_end}`, {
            method: "GET"
        }).then(response => response.json()) as priceById[]
        return response;
    }
)



export { getAllPriceById }
export const {setProductId, setDateEnd, setDateStart} = priceByIdSlice.actions;
export default priceByIdSlice.reducer