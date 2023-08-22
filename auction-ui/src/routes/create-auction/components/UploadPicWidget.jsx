import { useEffect, useRef } from "react";

const UploadPicWidget = ({setImgUrl}) => {
    const cloudinaryRef = useRef();
    const widgetRef = useRef();

    useEffect(() => {
        cloudinaryRef.current = window.cloudinary;
        widgetRef.current = cloudinaryRef.current.createUploadWidget({
            cloudName: 'driu8s9eg',
            uploadPreset: 'nlqdg5vz',
            sources: ['local', 'url'],
        }, function(error, result) {
            if (result.event === "success") {
                setImgUrl(result.info.secure_url);
            }
        })
    }, [])

    return (
        <button className="btn btn-primary" onClick={() => widgetRef.current.open()}>Upload an image</button>
    )
}

export default UploadPicWidget;