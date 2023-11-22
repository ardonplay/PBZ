import TableHead from "./TableHead"
import TableNavigation from "./TableNavigation"
import TableRow from "./TableRow"

export default function Table(){
    return(
        <div class="w-full h-full flex flex-col">
            <div class="overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <TableHead columns={["Product Name", "Color", "Category", "Price", "Action"]}/>
                <tbody>
                    <TableRow data={["Macbook pro 16'", "Space Black", "Laptop", "$3999"]} id={1}/>
                    <TableRow data={["Apple Watch", "Black","Watches", "$199"]} id={2}/>
                    <TableRow data={["Apple iMac", "Silver","PC", "$2999"]} id={3}/>
                    <TableRow data={["Apple iMac", "Silver","PC", "$2999"]} id={4}/>
                    <TableRow data={["Apple iMac", "Silver","PC", "$2999"]} id={5}/>
                    <TableRow data={["Apple iMac", "Silver","PC", "$2999"]} id={6}/>
                </tbody>
            </table>
            </div>
         
           <TableNavigation/>
        </div>
        )
}