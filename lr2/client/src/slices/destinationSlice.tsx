import { PayloadAction, createAsyncThunk, createSlice } from '@reduxjs/toolkit'

export interface destinationRow {
    id: number
    name: string,
    region: string,
    country: string
}

interface destinationState {
    list: destinationRow[],
    selectedRow: destinationRow,
    editDialogOpen: boolean,
    addNewDialogOpen: boolean,
}

const initialState = {
    list: [],
    selectedRow: {id: 0, name: "", region: "", country: ""},
    editDialogOpen: false,
    addNewDialogOpen: false,
} as destinationState

const destinationSlice = createSlice({
    name: 'destinations',
    initialState,
    reducers: {
        setDestinations(state, action: PayloadAction<destinationRow[]>) {
            state.list = action.payload;
        },
        setSelectedRow(state, action: PayloadAction<destinationRow>) {
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
        builder.addCase(deleteDestination.fulfilled, (state, action) => {
            state.list = state.list.filter(row => row.id !== action.payload.id);
            state.editDialogOpen = false
        }),

            builder.addCase(updateDestination.fulfilled, (state, action) => {

                state.list = state.list.map(row => (row.id == action.payload.id) ? action.payload : row)
                state.editDialogOpen = false;
            }),
            builder.addCase(getAllDestinations.fulfilled, (state, action) => {
                state.list = action.payload
            }),

            builder.addCase(addNewDestination.fulfilled, (state, action) => {
                state.list = [...state.list, action.payload]
                state.addNewDialogOpen = false

            })


    }
})



const deleteDestination = createAsyncThunk(
    'destinations/delete',
    async (data: destinationRow) => {
        const response = await fetch("http://localhost:8080/api/v1/destinations", {
            method: "DELETE", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as destinationRow;

        return response
    }
)

const updateDestination = createAsyncThunk(
    'destinations/update',
    async (data: destinationRow) => {
        console.log("update:", data)
        const response = await fetch("http://localhost:8080/api/v1/destinations", {
            method: "PATCH", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as destinationRow;
        return response;
    }
)

const getAllDestinations = createAsyncThunk(
    'destinations/getAll',
    async () => {
        const response = await fetch("http://localhost:8080/api/v1/destinations", {
            method: "GET"
        }).then(response => response.json()) as wrapper;
        return response.items as destinationRow[];
    }
)
const addNewDestination = createAsyncThunk(
    'destinations/add',
    async (data: destinationRow) => {
        const response =  await fetch("http://localhost:8080/api/v1/destinations", {
            method: "POST", headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }).then(response => response.json()) as destinationRow;
        return response;
    }
)


export const { setSelectedRow, setEditDialogOpen, setAddNewDialogOpen, setDestinations } = destinationSlice.actions

export { updateDestination, deleteDestination, getAllDestinations, addNewDestination}

export default destinationSlice.reducer