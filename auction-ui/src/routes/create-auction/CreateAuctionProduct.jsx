import AddProduct from "./components/AddProduct";

export default function CreateAuctionProduct({currentUser}) {
    return (
        <div className="container">
            <h3 className="pt-4">Add a product</h3>
            <AddProduct currentUser={currentUser} />
        </div>
    )
}