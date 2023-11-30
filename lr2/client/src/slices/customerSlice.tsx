import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'


export interface customerRow {
    id: string,
    name: string,
    type: string,
    address: string, 
    phone_number: string,
    bankDetails: bankDetails
}

interface bankDetails {
    id: number, 
    number: string, 
    name: string
}

interface customerState {
    list: customerRow[],
    selectedRow: customerRow,
    editDialogOpen: boolean,
    addNewDialogOpen: boolean,
}

const initialState = {
    list: [],
    selectedRow: { id: "", name: "", type: "", address: "", phone_number: "", bankDetails: {id: 0, number: "", name: ""} },
    editDialogOpen: false,
    addNewDialogOpen: false,
} as customerState

const customerSlice = createSlice({
    name: 'customers',
    initialState,
    reducers: {
        setCustomers(state, action: PayloadAction<customerRow[]>) {
            state.list = action.payload;
        },
        setSelectedRow(state, action: PayloadAction<customerRow>) {
            state.selectedRow = action.payload
        },
        setEditDialogOpen(state, action: PayloadAction<boolean>) {
            state.editDialogOpen = action.payload
        },
        setAddNewDialogOpen(state, action: PayloadAction<boolean>) {
            state.addNewDialogOpen = action.payload
        }
    },

    extraReducers: (builder) => {
        builder.addCase(deleteCustomer.fulfilled, (state, action) => {
            state.list = state.list.filter(row => row.id !== action.payload.id);
            state.editDialogOpen = false
        }),

            builder.addCase(updateCustomer.fulfilled, (state, action) => {

                state.list = state.list.map(row => (row.id == action.payload.id) ? action.payload : row)
                state.editDialogOpen = false;
            }),
            builder.addCase(getAllCustomers.fulfilled, (state, action) => {
                state.list = action.payload
            }),

            builder.addCase(addNewCustomer.fulfilled, (state, action) => {
                state.list = [...state.list, action.payload]
                state.addNewDialogOpen = false

            })


    }
})



const deleteCustomer = createAsyncThunk(
    'customers/delete',
    async (data: customerRow) => {
        const response = await fetch("http://localhost:8080/api/v1/customers", {
            method: "DELETE", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as customerRow;

        return response
    }
)

const updateCustomer = createAsyncThunk(
    'customers/update',
    async (data: customerRow) => {
        console.log("update:", data)
        const response = await fetch("http://localhost:8080/api/v1/customers", {
            method: "PATCH", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as customerRow;
        return response;
    }
)

const getAllCustomers = createAsyncThunk(
    'customers/getAll',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/customers", {
            method: "GET"
        }).then(response => response.json()) as wrapper;
        return response.items as customerRow[];
    }
)
const addNewCustomer = createAsyncThunk(
    'customers/add',
    async (data: customerRow) => {
        const response =  await fetch("http://localhost:8080/api/v1/customers", {
            method: "POST", headers: { "Content-Type": "application/json" },
            body: JSON.stringify({name: data.name, type: data.type})
        }).then(response => response.json()) as customerRow;
        return response;
    }
)


export const { setSelectedRow, setEditDialogOpen, setAddNewDialogOpen, setCustomers } = customerSlice.actions

export { updateCustomer, deleteCustomer, getAllCustomers, addNewCustomer}

export default customerSlice.reducer