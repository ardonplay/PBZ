import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import axios from 'axios'

export interface productRow {
    id: number,
    name: string,
    type: string,
    edited?: boolean
}
export interface productState {
    list: productRow[],
    selectedRow: productRow,
    editDialogOpen: boolean,
    addNewDialogOpen: boolean,
    types: string[]
}

const initialState = {
    list: [
        {
            id: 1,
            name: "ATM",
            type: "INDUSTRIAL",
            edited: false
        },
        {
            id: 2,
            name: "Watches",
            type: "HOUSEHOLD",
            edited: false
        },
        {
            id: 3,
            name: "KIOSK",
            type: "COMMERCE",
            edited: false
        }
    ],
    selectedRow: { id: 0, name: "", type: "Select Type" },
    editDialogOpen: false,
    addNewDialogOpen: false,
    types: ["INDUSTRIAL", "HOUSEHOLD", "COMMERCE"]
} as productState


const productSlice = createSlice({
    name: 'products',
    initialState,
    reducers: {
        setProducts(state, action: PayloadAction<productRow[]>) {
            state.list = action.payload;
        },
    
        update(state, action: PayloadAction<productRow>) {
            state.list = state.list.map(row => (row.id == action.payload.id) ? action.payload : row)
            axios.patch("http://localhost:8080/api/v1/products", action.payload).then();
            state.editDialogOpen = false;
        },
        setSelectedRow(state, action: PayloadAction<productRow>) {
            state.selectedRow = action.payload
        },
        setEditDialogOpen(state, action: PayloadAction<boolean>) {
            state.editDialogOpen = action.payload
        },
        setAddNewDialogOpen(state, action: PayloadAction<boolean>) {
            state.addNewDialogOpen = action.payload
        },
        setProductTypes(state, action: PayloadAction<string[]>) {
            state.types = action.payload
        },
        addNewProduct(state, action: PayloadAction<productRow>) {
           await axios.post("http://localhost:8080/api/v1/products", action.payload);
            state.addNewDialogOpen = false
        },
        deleteProduct(state, action: PayloadAction<productRow>) {
            state.list = state.list.filter(row => row.id !== action.payload.id);
            state.editDialogOpen = false
        }
    },
})

export const { update, setSelectedRow, setEditDialogOpen, setAddNewDialogOpen, setProductTypes, deleteProduct, addNewProduct, setProducts } = productSlice.actions
export default productSlice.reducer