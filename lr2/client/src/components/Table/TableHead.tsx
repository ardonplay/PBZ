export default function TableHead(props: {columns: string[]}) {

    return (
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                {props.columns.map((col, iter) =>
                    <th scope="col" class="px-6 py-3" key={iter}>
                        {col}
                    </th>
                )}
            </tr>
        </thead>
    )
}