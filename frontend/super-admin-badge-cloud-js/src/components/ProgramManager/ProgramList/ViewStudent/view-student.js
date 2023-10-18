import React from 'react'
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';

const style = {
  position: 'absolute' ,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

export const ViewStudennt = ({index,record,disableStudentFromProgram,classes,programId,setProgramStudentList,SetDeleteStudent}) => {


  const handleIssuingBadge = () => {
    disableStudentFromProgram(record,walletType)
    handleClose()
  }


  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);



  const [walletType,setWalletType]= React.useState("native wallet");



  return (
    <tr key={index}>
                                                                    <td>{record?.studentName}</td>
                                                                    <td>{record?.revokeStatus?.toString() || "fasdfs"}</td>
                                                                    <td>{(record?.expires && !record?.expireStatus)? record?.expires : "Does not Expires"}</td>
                                                                    <td>
                                                                        {
                                                                        record?.status
                                                                        ? <a className={`${classes.disableBtn} btn btn-disable-collection fas fa-ban me-2`} onClick={() => disableStudentFromProgram(record)}><i  ></i>Disable collection</a>
                                                                        : <a className={`${classes.enableBtn} btn btn-enable-collection`} onClick={() =>{
console.log("ok")
handleOpen()

                                                                        }
                                                                        }

                                                                  ><i className="bi bi-check-all me-2"></i>Enable collection</a>
                                                                        }

<div>

<Modal
  open={open}
  onClose={handleClose}
  aria-labelledby="modal-modal-title"
  aria-describedby="modal-modal-description"
>
  <Box sx={style}>
    <Typography id="modal-modal-title" variant="h6" component="h2">
    Choose appropriate wallet:
    </Typography>
    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
    <select
                              name='basic-datatable_length'
                              aria-controls='basic-datatable'
                              className='form-select form-select-sm'

                              onChange={
                                (e) => {
                                  setWalletType(e.target.value)
                                }
                              }
                            >

                                  <option  value="native wallet">
                                  Native wallet
                                  </option>
                                  <option  value="velocity">
                                    Velocity
                                  </option>


                            </select>
<br></br>
                            <button className={`${classes.enableBtn} btn btn-enable-collection`}
                            onClick={handleIssuingBadge}

                                                                  >Submit</button>
    </Typography>
  </Box>
</Modal>
</div>
                                                                    </td>

                                                                    <td><>{record?.wallet?.toString() || "No wallet"}


                                                                  </>
                                                                    </td>
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
                                                                                <a href="#viewStudentRevokeStatus" data-bs-toggle="modal" role="button" class="dropdown-item"
                                                                                    onClick={() => {
                                                                                        record['programId'] = programId;
                                                                                        setProgramStudentList(record || [])
                                                                                    }}
                                                                                >Edit revoke status</a>
                                                                                <a
                                                                                    href="#viewStudentExpirationStatus"
                                                                                    data-bs-toggle="modal"
                                                                                    role="button"
                                                                                    class="dropdown-item"
                                                                                    onClick={() => setProgramStudentList(record || [])}
                                                                                >Edit Expiration Date</a>
                                                                                <a
                                                                                    href='#delete'
                                                                                    data-bs-toggle='modal'
                                                                                    role='button'
                                                                                    className='dropdown-item text-danger'
                                                                                    onClick={() => SetDeleteStudent(record.studentId)}
                                                                                >
                                                                                    Delete
                                                                                </a>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                </tr>
  )
}
