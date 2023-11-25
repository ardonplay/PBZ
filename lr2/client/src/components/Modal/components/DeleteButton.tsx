export default function DeleteButton(props: { onClick: Function }) {
    return (
        <button onClick={() => props.onClick()}
            class="text-white inline-flex items-center bg-red-800 hover:bg-red-900 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-800 dark:hover:bg-red-00 dark:focus:ring-red-800">
                Delete
        </button>
    )
}