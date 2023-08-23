export default function ProductCard({product}) {

    console.log(product)
    return (
        <div className="card pb-2">
            <div className="row g-0">
                <div className="col-md-4">
                    <img src={product.url} className="card-img" alt=""/>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{product.name}</h5>
                        <p className="card-text">{product.description}</p>
                        <a href="#" className="card-link">Create Auction</a>
                    </div>
                </div>
            </div>
        </div>  
    )
}