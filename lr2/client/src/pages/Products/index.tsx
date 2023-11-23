import { useState } from "preact/hooks";
import ProductEditModal from "../../components/Modal/ProductEditModal";
import TableHead from "../../components/Table/TableHead";
import TableNavigation from "../../components/Table/TableNavigation";
import TableRow from "../../components/Table/TableRow";

export default function Products() {
    const [open, setOpen] = useState(false)
    return (
        <>
            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Products</span>
            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["id", "Name", "Type", "Action"]} />
                        <tbody>
                            <TableRow data={["1", "Aboba", "Laptop"]} id={1} onEdit={setOpen} editModal={ProductEditModal({setOpen: setOpen, open: open})} />
                            <TableRow data={["2", "Black", "Watches"]} id={2} onEdit={setOpen}  editModal={ProductEditModal({setOpen: setOpen, open: open})}/>
                            <TableRow data={["3", "Silver", "PC"]} id={3} onEdit={setOpen} editModal={ProductEditModal({setOpen: setOpen, open: open})}/>
                            <TableRow data={["4", "Silver", "PC"]} id={4} onEdit={setOpen} editModal={ProductEditModal({setOpen: setOpen, open: open})}/>
                            <TableRow data={["5", "Silver", "PC"]} id={5} onEdit={setOpen} editModal={ProductEditModal({setOpen: setOpen, open: open})}/>
                            <TableRow data={["6", "Silver", "PC"]} id={6} onEdit={setOpen} editModal={ProductEditModal({setOpen: setOpen, open: open})}/>
                        </tbody>
                    </table>
                </div>
                <TableNavigation />
            </div>
        </>
    )
}