import React, { useState, useEffect } from "react";
import { Table, Spinner } from "react-bootstrap";
import { Trash, Key } from 'react-bootstrap-icons';
import CustomPagination from "../utils/CustomPagination.component.jsx";
import AdminService from "../../../services/admin.service.jsx";

const DataTable = (props) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [dataPerPage] = useState(10);

    useEffect(() => {
        const fetchData = async () => {
            const result = await AdminService.getAllUsers(currentPage, dataPerPage);
            setData(result.data);
            setLoading(false);
        };
        fetchData();
    }, [currentPage, dataPerPage]);

    // Get current data
    const indexOfLastData = currentPage * dataPerPage;
    const indexOfFirstData = indexOfLastData - dataPerPage;
    const currentData = data.slice(indexOfFirstData, indexOfLastData);

    // Change page
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div>
            {loading ? (
                <Spinner animation="border" variant="primary" />
            ) : (
                <>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Id</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {currentData.map((item, index) => (
                            <tr key={item.id}>
                                <td>{index + 1}</td>
                                <td>{item.id}</td>
                                <td>{item.username}</td>
                                <td>{item.password}</td>
                                <td>{item.email}</td>
                                <td>{item.roles[0].name}</td>
                                <td ><a onClick={() => props.onDelete(item)} id="btnDeleteUser"
                                            className="btn btn-sm btn-danger mr-2"><Trash/></a>
                                <a id="btnResetPassword" className="btn btn-sm btn-success"><Key/></a></td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <CustomPagination
                        dataPerPage={dataPerPage}
                        totalData={data.length}
                        paginate={paginate}
                        currentPage={currentPage}
                    />
                </>
            )}
        </div>
    );
};

export default DataTable;
