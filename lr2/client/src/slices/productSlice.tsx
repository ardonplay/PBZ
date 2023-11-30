import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

export interface productType {
    id: number,
    name: string
}

export interface productRow {
    id: number,
    name: string,
    type: productType
}

interface productState {
    list: productRow[],
    selectedRow: productRow,
    editDialogOpen: boolean,
    addNewDialogOpen: boolean,
    types: productType[]
}

const initialState = {
    list: [],
    selectedRow: { id: 0, name: "", type: {} },
    editDialogOpen: false,
    addNewDialogOpen: false,
    types: []
} as productState

const productSlice = createSlice({
    name: 'products',
    initialState,
    reducers: {
        setProducts(state, action: PayloadAction<productRow[]>) {
            state.list = action.payload;
        },
        setSelectedRow(state, action: PayloadAction<productRow>) {
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
        builder.addCase(deleteProduct.fulfilled, (state, action) => {
            state.list = state.list.filter(row => row.id !== action.payload.id);
            state.editDialogOpen = false
        }),

            builder.addCase(updateProduct.fulfilled, (state, action) => {

                state.list = state.list.map(row => (row.id == action.payload.id) ? action.payload : row)
                state.editDialogOpen = false;
            }),
            builder.addCase(getProductTypes.fulfilled, (state, action) => {
                state.types = action.payload
                state.selectedRow = {...state.selectedRow, type: state.types[0]}
            }),

            builder.addCase(getAllProducts.fulfilled, (state, action) => {
                state.list = action.payload
            }),

            builder.addCase(addNewProduct.fulfilled, (state, action) => {
                state.list = [...state.list, action.payload]
                state.addNewDialogOpen = false

            })


    }
})



const deleteProduct = createAsyncThunk(
    'products/delete',
    async (data: productRow) => {
        const response = await fetch("http://localhost:8080/api/v1/products", {
            method: "DELETE", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as productRow;

        return response
    }
)

const updateProduct = createAsyncThunk(
    'products/update',
    async (data: productRow) => {
        console.log("update:", data)
        const response = await fetch("http://localhost:8080/api/v1/products", {
            method: "PATCH", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as productRow;
        return response;
    }
)

const getProductTypes = createAsyncThunk(
    'products/types',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/products/types", {
            method: "GET"
        }).then(response => response.json()) as wrapper
        return response.items;
    }
)

const getAllProducts = createAsyncThunk(
    'products/getAll',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/products", {
            method: "GET"
        }).then(response => response.json()) as wrapper;
        return response.items;
    }
)
const addNewProduct = createAsyncThunk(
    'products/add',
    async (data: productRow) => {
        console.log("upload:", data)
        const response =  await fetch("http://localhost:8080/api/v1/products", {
            method: "POST", headers: { "Content-Type": "application/json" },
            body: JSON.stringify({name: data.name, type: data.type})
        }).then(response => response.json()) as productRow;
        return response;
    }
)


export const { setSelectedRow, setEditDialogOpen, setAddNewDialogOpen, setProducts } = productSlice.actions

export { updateProduct, deleteProduct, getAllProducts, getProductTypes, addNewProduct}

export default productSlice.reducer