import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import { productRow } from './productSlice'
import { destinationRow } from './destinationSlice'

export interface waybillRow {
    id: number,
    customer_id: string,
    date: string,
    destination: destinationRow,
    waybill_products: waybillProduct[]
}

export interface waybillProduct {
    id: number
    count: number,
    price: number,
    product: productRow
}

interface waybillState {
    list: waybillRow[],
    newProductRow: waybillProduct
    selectedRow: waybillRow,
    editDialogOpen: boolean,
    addNewDialogOpen: boolean,
    addProductToWaybillDialogOpen: boolean
}

interface wrapper {
    items: [],
    totalCount: number
}



const initialState = {
    list: [],
    newProductRow: { id: 0, count: 0, price: 0, product: { id: 0, name: "" } },
    selectedRow: { id: 0, customer_id: "", date: "", destination: {}, waybill_products: [] },
    editDialogOpen: false,
    addNewDialogOpen: false,
    addProductToWaybillDialogOpen: false
} as waybillState

const waybillSlice = createSlice({
    name: 'waybills',
    initialState,
    reducers: {
        setWaybills(state, action: PayloadAction<waybillRow[]>) {
            state.list = action.payload;
        },
        setSelectedRow(state, action: PayloadAction<waybillRow>) {
            state.selectedRow = action.payload
            console.log(state.selectedRow)
        },
        setEditDialogOpen(state, action: PayloadAction<boolean>) {
            state.editDialogOpen = action.payload
        },
        setAddNewDialogOpen(state, action: PayloadAction<boolean>) {
            state.addNewDialogOpen = action.payload
        },
        setNewProductRow(state, action: PayloadAction<waybillProduct>) {
            state.newProductRow = action.payload
        },
        addNewProductToWaybill(state, action: PayloadAction<waybillProduct>) {
            state.selectedRow.waybill_products = [...state.selectedRow.waybill_products, action.payload]
            state.newProductRow = {id: 0, count: 0, price: 0, product: { id: 0, name: "", type: { id: 0, name: "" } } }
            state.addProductToWaybillDialogOpen = false;
        },
        setAddProductToWaybillDialogOpen(state, action: PayloadAction<boolean>) {
            state.addProductToWaybillDialogOpen = action.payload
        },
        deleteProductFromWaybill(state, action: PayloadAction<waybillProduct>) {
            state.selectedRow = { ...state.selectedRow, waybill_products: state.selectedRow.waybill_products.filter(product => JSON.stringify(product) !== JSON.stringify(action.payload)) }
            console.log(state.selectedRow)
        }
    },

    extraReducers: (builder) => {
        builder.addCase(deleteWaybill.fulfilled, (state, action) => {
            state.list = state.list.filter(row => row.id !== action.payload.id);
            state.editDialogOpen = false
        }),

            builder.addCase(updateWaybill.fulfilled, (state, action) => {

                state.list = state.list.map(row => (row.id == action.payload.id) ? action.payload : row)
                state.editDialogOpen = false;
            }),

            builder.addCase(getAllWaybills.fulfilled, (state, action) => {
                state.list = action.payload as waybillRow[]
            }),

            builder.addCase(addNewWaybill.fulfilled, (state, action: PayloadAction<waybillRow>) => {
                console.log(action.payload)
                state.selectedRow = { id: 0, customer_id: "", date: "", destination: {id: 0, country: "", region: "", name: ""}, waybill_products: [] }
                state.list = [...state.list, action.payload]
                state.addNewDialogOpen = false
        
            })

            builder.addCase(getFullWaybill.fulfilled, (state, action) => {
                state.selectedRow = action.payload as waybillRow
            })

    }
})

const deleteWaybill = createAsyncThunk(
    'waybills/delete',
    async (data: waybillRow) => {
        const response = await fetch("http://localhost:8080/api/v1/waybills", {
            method: "DELETE", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as waybillRow;

        return response
    }
)

const updateWaybill = createAsyncThunk(
    'waybills/update',
    async (data: waybillRow) => {

        const response = await fetch("http://localhost:8080/api/v1/waybills", {
            method: "PATCH", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as waybillRow;
        return response;
    }
)

const getAllWaybills = createAsyncThunk(
    'waybills/getAll',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/waybills", {
            method: "GET"
        }).then(response => response.json()) as wrapper;
        return response.items;
    }
)


const getFullWaybill = createAsyncThunk(
    'waybills/get',
    async (id: number) => {
        const response = await fetch(`http://localhost:8080/api/v1/waybills?id=${id}`, {
            method: "GET"
        }).then(response => response.json()) as waybillRow;
        return response;
    }
)

const addNewWaybill = createAsyncThunk(
    'waybills/add',
    async (data: waybillRow) => {
        console.log("upload:", data)
        const response = await fetch("http://localhost:8080/api/v1/waybills", {
            method: "POST", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as waybillRow;
        return response;
    }
)



export const { setSelectedRow, setEditDialogOpen, setAddNewDialogOpen, setWaybills, setNewProductRow, setAddProductToWaybillDialogOpen, addNewProductToWaybill, deleteProductFromWaybill } = waybillSlice.actions

export { updateWaybill, deleteWaybill, getAllWaybills, addNewWaybill, getFullWaybill }

export default waybillSlice.reducer