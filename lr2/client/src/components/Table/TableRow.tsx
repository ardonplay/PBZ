export default function TableRow(props: { id: number, data: string[], action?: any }) {


    return (
        <>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                {props.data.map(col => <td class="px-6 py-4">
                    {col}
                </td>)}
                {props.action ? <td class="px-6 py-4">
                    {props.action}
                </td> : null}

            </tr>
        </>

    )
}