import { makeStyles, TablePagination } from '@material-ui/core';
import { useEffect } from 'react';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';
import { ProgramApi } from '../../../../api/ProgramAPI';
import { toastActions } from '../../../../Redux/store/Slices/toast';
import { ViewCollectionModal } from '../ViewCollections/view-modal';
import { ViewStudentModal } from '../ViewProgramStudents/view-modal';
import { ExpirationDateModal } from './EditExpirationDate';
import { EditRevokeStatus } from './EditRevokeStatus';
import { ViewStudennt } from './view-student';


const useStyles = makeStyles((theme) => ({
    tablePagination: {
        paddingRight: '27px !important',
    },
    center: {
        textAlign: 'center'
    },
    disableBtn: {
        background: "#DBDBEE",
        color: "#8D8BCA",
        borderRadius: "5px",
        transition: "var(--transition)",
        '&:hover': {
            background: "#0909B4",
            color: "#fcfcfc"
        }
    },
    enableBtn: {
        background: "#abe7cb",
        color: "#257c54",
        borderRadius: "5px",
        transition: "var(--transition)",
        '&:hover': {
            background: "#119f5d",
            color: "#fcfcfc",
        }
    },
}));
const ViewStudent = () => {
    const classes = useStyles();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const programId = searchParams.get('program-id');
    const [rerenderFlag, setRerenderFlag] = useState(false);
    const { tenantId } = useSelector((state) => state.jwt);
    const dispatch = useDispatch();
    const [records, setRecords] = useState([]);
    const [programStudentList, setProgramStudentList] = useState([]);
    const [deleteRecord, setDeleteRecord] = useState(null);
    const [totalElements, setTotalElements] = useState(0);
    const [deleteStudent, SetDeleteStudent] = useState(null);
    const [page, setPage] = useState(0);
    const [limit, setLimit] = useState(10);
    const rowsPerPageOptions = [10, 25, 50, 100];
    const getAllList = async () => {
        try {
            const response = await ProgramApi.getStudentByProgramId(programId, page, limit);
            console.log('response', response);
            setRecords(response?.content || []);
            setTotalElements(response?.totalElements || 0);
        } catch (error) {
            setRecords([]);
            setTotalElements(0);
        }
    };
    const editById = async (data) => {
        const body = {
            ...data,
            isActivated: !data.isActivated,
        };
        try {
            const res = await ProgramApi.edit(body);
            await dispatch(
                toastActions.succesMessage({
                    message: res.message,
                })
            );
            getAllList();
        } catch (error) {
            await dispatch(
                toastActions.succesMessage({
                    message: error.message,
                })
            );
        }
    };

    const deleteHandler = async (id) => {
        setDeleteRecord(id);
    };

    const getAndDeleteById = async () => {
        try {
            await ProgramApi.deleteById(deleteRecord);
            setDeleteRecord(null);
        } catch (error) { }
    };

    const toggleHandler = (record) => {
        editById(record);
    };

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    };

    const handleLimitChange = (event) => {
        setPage(0);
        setLimit(parseInt(event.target.value, 10));
    };
    const rowsPerPageOptionsHandler = (e) => {
        setLimit(e.target.value);
    };

    const disableStudentFromProgram = async (rec,walletType) => {
        try {
            const body = {
                studentId: rec.studentId,
                programId: parseInt(programId),
                isEnableCollection: !rec.status,
                walletType:walletType

            }
            await ProgramApi.disableStudentFromProgram(body);
            setRerenderFlag(!rerenderFlag)
        }
        catch (error) {

        }
    }

    const deleteStudentFromProgram = async () => {
        try {
            await ProgramApi.deleteStudentFromProgram(programId, deleteStudent);
            window.location.href =`list-of-students?program-id=${programId}`;
        }
        catch (error) {

        }
    }

    useEffect(() => {
        getAllList();
    }, [deleteRecord, page, limit, rerenderFlag, deleteStudent]);
    return (
        <>
            <EditRevokeStatus record={programStudentList} rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag} />
            <ExpirationDateModal record={programStudentList} rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag} />
            <div className='content-page'>
                <div>
                    <div className='row'>
                        <div className='col-12'>
                            <div className='page-title-box'>

                                <h4 className='page-title'>List of Students</h4>
                            </div>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col-12'>
                            <div className='card'>
                                <div className='card-body'>
                                    <div
                                        id='basic-datatable_wrapper'
                                        className='dataTables_wrapper dt-bootstrap5 no-footer'
                                    >
                                        <div className='row'>
                                            <div className='col-sm-12 col-md-6'>
                                                <div
                                                    className='dataTables_length'
                                                    id='basic-datatable_length'
                                                >
                                                    <label className='form-label'>
                                                        Show
                                                        <select
                                                            name='basic-datatable_length'
                                                            aria-controls='basic-datatable'
                                                            className='form-select form-select-sm'
                                                            onChange={rowsPerPageOptionsHandler}
                                                        >
                                                            {rowsPerPageOptions?.map((rowsPerPage, index) => {
                                                                return (
                                                                    <option key={index} value={rowsPerPage}>
                                                                        {rowsPerPage}
                                                                    </option>
                                                                );
                                                            })}
                                                        </select>{' '}
                                                        entries
                                                    </label>
                                                </div>
                                            </div>
                                            <div className='col-sm-12 col-md-6'>
                                                <div
                                                    id='basic-datatable_filter'
                                                    className='dataTables_filter'
                                                >
                                                    <label>
                                                        Search:
                                                        <input
                                                            type='search'
                                                            className='form-control form-control-sm'
                                                            placeholder
                                                            aria-controls='basic-datatable'
                                                        />
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div className='row'>
                                            <div className='col-sm-12'>
                                                <table
                                                    id='basic-datatable'
                                                    className='table table-striped dt-responsive nowrap align-middle w-100 dataTable no-footer dtr-inline'
                                                    aria-describedby='basic-datatable_info'
                                                    style={{ position: 'relative', width: 1557 }}
                                                >
                                                    <thead>
                                                        <tr>
                                                            <th
                                                                className='sorting sorting_asc'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '217.8px' }}
                                                                aria-label='Order: activate to sort column descending'
                                                                aria-sort='ascending'
                                                            >
                                                                Student Name
                                                            </th>
                                                            <th
                                                                className='sorting'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '416.8px' }}
                                                                aria-label='user Name: activate to sort column ascending'
                                                            >
                                                                Revoke Status
                                                            </th>
                                                            <th
                                                                className='sorting_disabled action-datatable sorting'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '270.8px' }}
                                                                aria-label='Category: activate to sort column ascending'
                                                            >
                                                                Expiration Date
                                                            </th>
                                                            <th
                                                                className='sorting_disabled action-datatable sorting'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '270.8px' }}
                                                                aria-label='Username: activate to sort column ascending'
                                                            >
                                                                Status
                                                            </th>
                                                            <th
                                                                className='sorting_disabled action-datatable sorting'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '270.8px' }}
                                                                aria-label='Username: activate to sort column ascending'
                                                            >
                                                                Wallet
                                                            </th>
                                                            <th
                                                                className='sorting_disabled action-datatable sorting'
                                                                tabIndex={0}
                                                                aria-controls='basic-datatable'
                                                                rowSpan={1}
                                                                colSpan={1}
                                                                style={{ width: '233.8px' }}
                                                                aria-label='Action: activate to sort column ascending'
                                                            >
                                                                Action
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {records?.map((record, index) => {
                                                            return (
                                                               <ViewStudennt index={index} record={record} disableStudentFromProgram={disableStudentFromProgram} classes={classes} programId={programId} setProgramStudentList={setProgramStudentList} SetDeleteStudent={SetDeleteStudent}/>
                                                            );
                                                        })}
                                                    </tbody>
                                                </table>
                                                {records?.length === 0 && <div className={classes.center}>No Data found!</div>}
                                            </div>
                                        </div>
                                        <TablePagination
                                            className={classes.tablePagination}
                                            // labelRowsPerPage={t('clients_table_row_per_page')}
                                            component='div'
                                            count={totalElements}
                                            rowsPerPageOptions={[]}
                                            onPageChange={handlePageChange}
                                            onRowsPerPageChange={handleLimitChange}
                                            page={page}
                                            rowsPerPage={limit}
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div>
                        <div
                            className='modal fade'
                            id='viewPrograms'
                            tabIndex={-1}
                            aria-labelledby='viewProgramsLabel'
                            aria-hidden='true'
                        >
                            <div className='modal-dialog modal-dialog-centered'>
                                <div className='modal-content'>
                                    <div className='modal-header modal-colored-header bg-primary'>
                                        <h4 className='modal-title' id='viewProgramsLabel'>
                                            List of Programs
                                        </h4>
                                        <button
                                            type='button'
                                            className='btn-close'
                                            data-bs-dismiss='modal'
                                            aria-hidden='true'
                                        />
                                    </div>
                                    <div className='modal-body table-responsive'>
                                        <table
                                            className='table table-centered table-striped align-middle'
                                            id='table_id'
                                        >
                                            <tbody>
                                                <tr>
                                                    <td>Program 1</td>
                                                    <td>
                                                        <a
                                                            href='edit-program.html'
                                                            className='btn btn-outline-primary btn-sm'
                                                        >
                                                            Edit
                                                        </a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Program 2</td>
                                                    <td>
                                                        <a
                                                            href='edit-program.html'
                                                            className='btn btn-outline-primary btn-sm'
                                                        >
                                                            Edit
                                                        </a>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div
                            id='delete'
                            className='modal fade'
                            tabIndex={-1}
                            role='dialog'
                            aria-hidden='true'
                        >
                            <div className='modal-dialog modal-sm'>
                                <div className='modal-content'>
                                    <div className='modal-body p-4'>
                                        <div className='text-center'>
                                            <i className='dripicons-warning h1 text-danger' />
                                            <h4 className='mt-2'>Are you sure?</h4>
                                            <button
                                                type='button'
                                                className='btn btn-danger mx-1 my-2'
                                                data-bs-dismiss='modal'
                                                onClick={() => {
                                                        deleteStudentFromProgram();
                                                }
                                               }
                                            >
                                                Yes
                                            </button>
                                            <button
                                                type='button'
                                                className='btn btn-outline-danger mx-1 my-2'
                                                data-bs-dismiss='modal'
                                            >
                                                No
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default ViewStudent;
