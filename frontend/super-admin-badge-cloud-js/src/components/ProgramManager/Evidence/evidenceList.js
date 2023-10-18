import { makeStyles, TablePagination } from '@material-ui/core';
import { useEffect } from 'react';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { EvidenceApi } from '../../../api/evidenceApi';
import { toastActions } from '../../../Redux/store/Slices/toast';

const useStyles = makeStyles((theme) => ({
  tablePagination: {
    paddingRight: '27px !important',
  },
  center: {
    textAlign: 'center'
  }
}));
const EvidenceList = () => {
  const { tenantId } = useSelector((state) => state.jwt);
  const classes = useStyles();
  const dispatch = useDispatch();
  const [records, setRecords] = useState([]);
  const [deleteRecord, setDeleteRecord] = useState(null);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [limit, setLimit] = useState(10);
  const rowsPerPageOptions = [10, 25, 50, 100];
  const getAllList = async () => {
    try {
      const response = await EvidenceApi.getAll(tenantId, page, limit);
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
      const res = await EvidenceApi.edit(body);
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
      await EvidenceApi.deleteById(deleteRecord);
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

  useEffect(() => {
    getAllList();
  }, [deleteRecord, page, limit]);
  console.log("records", records)
  return (
    <div className='content-page'>
      <div>
        <div className='row'>
          <div className='col-12'>
            <div className='page-title-box'>
              <div className='page-title-right'>
                <Link to='/program/add-evidence' className='btn btn-primary'>
                  Add Evidence
                </Link>
              </div>
              <h4 className='page-title'>Evidences List</h4>
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
                            {/* <th
                              className='sorting sorting_asc'
                              tabIndex={0}
                              aria-controls='basic-datatable'
                              rowSpan={1}
                              colSpan={1}
                              style={{ width: '217.8px' }}
                              aria-label='Order: activate to sort column descending'
                              aria-sort='ascending'
                            >
                              Order
                            </th> */}
                            <th
                              className='sorting'
                              tabIndex={0}
                              aria-controls='basic-datatable'
                              rowSpan={1}
                              colSpan={1}
                              style={{ width: '416.8px' }}
                              aria-label='Evidence Name: activate to sort column ascending'
                            >
                              Evidence Name
                            </th>
                            <th
                              className='sorting'
                              tabIndex={0}
                              aria-controls='basic-datatable'
                              rowSpan={1}
                              colSpan={1}
                              style={{ width: '416.8px' }}
                              aria-label='Evidence Name: activate to sort column ascending'
                            >
                              Link
                            </th>
                            {/* <th
                              className='sorting_disabled action-datatable sorting'
                              tabIndex={0}
                              aria-controls='basic-datatable'
                              rowSpan={1}
                              colSpan={1}
                              style={{ width: '270.8px' }}
                              aria-label='isVizzibl: activate to sort column ascending'
                            >
                              isVizzibl
                            </th> */}
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
                            const isChecked = record?.isActivated;
                            return (
                              <tr key={record?.id}>
                                <td>{record?.name}</td>
                                <td>{record?.link}</td>
                                <td>
                                  <div className='dropdown'>
                                    <a
                                      href='#'
                                      className='dropdown-toggle arrow-none card-drop p-0'
                                      data-bs-toggle='dropdown'
                                      aria-expanded='false'
                                    >
                                      <i className='mdi mdi-dots-vertical' />
                                    </a>
                                    <div className='dropdown-menu dropdown-menu-animated'>
                                      <a
                                        href='#viewPrograms'
                                        data-bs-toggle='modal'
                                        role='button'
                                        className='dropdown-item'
                                      >
                                        View programs
                                      </a>

                                      <Link
                                        to='/program/edit-evidence'
                                        class='dropdown-item'
                                        state={{ id: record?.id }}
                                      >
                                        Edit
                                      </Link>
                                      <a
                                        href='#delete'
                                        data-bs-toggle='modal'
                                        role='button'
                                        className='dropdown-item text-danger'
                                        onClick={() =>
                                          deleteHandler(record?.id)
                                        }
                                      >
                                        Delete
                                      </a>
                                    </div>
                                  </div>
                                </td>
                              </tr>
                            );
                          })}
                          {records?.length === 0 && <tr>No Data found!</tr>}
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <TablePagination
                    className={classes.tablePagination}
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
                      onClick={() => getAndDeleteById()}
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
  );
};

export default EvidenceList;
